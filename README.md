# quantum-leap
The backend of a tech events platform

## Status

[![CodeQL Advanced - Java](https://github.com/BatistaGabriel/quantum-leap/actions/workflows/codeql.yml/badge.svg)](https://github.com/BatistaGabriel/quantum-leap/actions/workflows/codeql.yml)

## Running the project

Before running the project using your preferred method, either through the IDE or the command line, ensure you start the database container by executing the following command:

```bash
docker compose up -d
```

## SDK Version

This project uses `sdkman` to manage the version. To install it, follow these [instructions](https://sdkman.io/install).  
Once it is installed, you must use the SDK version defined in the `.sdkmanrc` file by running the following command:

```bash
sdk env install
```
