FROM openshift/wildfly-101-centos7
EXPOSE 8080
EXPOSE 9990
ADD ROOT.war /wildfly/standalone/deployments/ROOT.war
RUN sh /wildfly/bin/standalone.sh
