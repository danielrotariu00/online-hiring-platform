FROM node:16-alpine AS build

WORKDIR /admin

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

CMD ["npm", "start"]