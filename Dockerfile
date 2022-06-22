From openjdk:11
copy ./target/amigos-0.0.1-SNAPSHOT.war amigos-0.0.1-SNAPSHOT.war
CMD ["java","-jar","amigos-0.0.1-SNAPSHOT.war"]