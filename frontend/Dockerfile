FROM --platform=linux/arm64 node:18.16.1-alpine as builder
WORKDIR /app

# build
COPY . /app
RUN yarn build:prod
#RUN yarn build:sb

# nginx 
FROM nginx:latest
RUN rm -rf /etc/nginx/conf.d
COPY conf /etc/nginx
COPY --from=builder /app/dist /usr/share/nginx/html
#COPY --from=builder /app/storybook-static /usr/share/nginx/html/storybook

EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
