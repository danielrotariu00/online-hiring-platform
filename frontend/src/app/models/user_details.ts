import { City } from "./city";
import { Country } from "./country";

export interface UserDetails {
  firstName: string;
  lastName: string;
  phoneNumber: string;
  cityId: number;
  address: string;
  profileDescription: string;
  profilePictureUrl: string;
  city?: City;
  country?: Country;
}
