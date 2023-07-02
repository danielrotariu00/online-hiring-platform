FROM node:16-alpine AS build

WORKDIR /users

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

CMD ["npm", "start"]