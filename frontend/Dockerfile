FROM node:20-alpine as build

WORKDIR /app
COPY . .

RUN yarn
RUN yarn build

FROM nginx:1.27.4-alpine
COPY nginx.conf /etc/nginx
COPY --from=build /app/dist /usr/share/nginx/html