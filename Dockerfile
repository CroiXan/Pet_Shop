FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/demo-0.0.2-SNAPSHOT.jar app.jar
COPY Wallet_YFDYCJNLO473ALK4 /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]