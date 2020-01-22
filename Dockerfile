FROM openjdk:8-jre-alpine
ARG username=n307768
ARG userid=1001990000
RUN echo "${username}:x:${userid}:${userid}::/home/${username}:" >> /etc/passwd && \
echo "${username}:!:$(($(date +%s) / 60 / 60 / 24)):0:99999:7:::" >> /etc/shadow && \
echo "${username}:x:${userid}:" >> /etc/group && \
mkdir /home/${username} && chown ${username}: /home/${username}
USER ${username}

ENV APPNAME=can_loan_ced.0.0.1-SNAPSHOT

ENV AMQ_BROKER_URL testBrokerUrl
ENV VLN.CED_401_INPUT_QNAME vlnced401inputQname
ENV VLN.CED_401_OUTPUT_QNAME vlnced401outputQname

ADD target/$APPNAME.jar $APPNAME.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "/can_loan_ced.0.0.1-SNAPSHOT.jar"]
EXPOSE 8052