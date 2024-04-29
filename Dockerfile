FROM dpage/pgadmin4:8.5
COPY ./pgadmin/config_distro.py /pgadmin4/

USER root
RUN apk update && apk add --upgrade libldap

USER pgadmin
