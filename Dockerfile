FROM postgres:15

ENV POSTGRES_DB=quantum-leap
ENV POSTGRES_USER=gabrielbatista
ENV POSTGRES_PASSWORD=password

EXPOSE 5432