package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.CompanyDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.repository.CompanyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ICityService cityService;

    @Autowired
    private MimeContentService mimeContentService;

    @Autowired
    private UrlService urlService;

    private final CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);

    @Value("${companies.images.path}")
    private String companiesImagesPath;

    private static final String NAME_ALREADY_IN_USE_MESSAGE = "Name <%s> is already in use.";
    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with id <%s> does not exist.";

    @Override
    public void createCompany(CompanyDTO request) {
        String name = request.getName();

        if (companyRepository.findCompanyByName(name).isPresent()) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_IN_USE_MESSAGE, name));
        }

        Company company = companyMapper.toModel(request);

        if(request.getCityId() != null) {
            City city = cityService.getCityOrElseThrowException(request.getCityId());
            company.setCityId(city.getId());
        }

        companyRepository.save(company);
    }

    @Override
    public CompanyDTO updateCompany(Long companyId, CompanyDTO request) {
        getCompanyOrElseThrowException(companyId);

        Company company = companyMapper.toModel(request);
        company.setId(companyId);

        if(request.getCityId() != null) {
            City city = cityService.getCityOrElseThrowException(request.getCityId());
            company.setCityId(city.getId());
        }

        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyDTO getCompany(Long id) {
        Company company = getCompanyOrElseThrowException(id);

        return companyMapper.toResponse(company);
    }

    @Override
    public Iterable<CompanyDTO> getCompanies() {

        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompany(Long id) {

        getCompanyOrElseThrowException(id);

        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompanyOrElseThrowException(Long id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public void saveImage(Long companyId, MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        String type = mimeContentService.getMimeType(imageBytes);

        if(!mimeContentService.isImage(type))
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);


        Company company = companyRepository.findById(companyId).orElse(null);

        if(company == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String extension = mimeContentService.getExtensionFromMimeType(type);

        if(company.getPhoto() != null) {
            String[] arr = company.getPhoto().split("/");
            String currentImagePath = arr[arr.length - 1];
            String oldPath = companiesImagesPath + "/" + currentImagePath;
            File file = new File(oldPath);
            file.delete();
        }

        String imageName = companyId + "." + extension;
        String path = companiesImagesPath + "/" + imageName;
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(imageBytes);
            fos.flush();
        }

        path = urlService.getBaseUrl() + "/images/companies/" + imageName;
        company.setPhoto(path);
        companyRepository.save(company);
    }
}
