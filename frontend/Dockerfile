FROM node:alpine

COPY ./ /app/

RUN apk update

WORKDIR /app/

RUN npm install && npm audit fix

#EXPOSE 5173
EXPOSE 8000

#CMD ["npm", "run", "start"]
#CMD ["npm", "run", "serve"]
CMD ["npm", "run", "dev"]