FROM openshift/wildfly-101-centos7
EXPOSE 8080
EXPOSE 9990
RUN sh /wildfly/bin/standalone.sh
