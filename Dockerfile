FROM openshift/wildfly-101-centos7
CMD mvn package -e 
ADD target/ROOT.war /wildfly/standalone/deployments/ROOT.war
RUN sh /wildfly/bin/standalone.sh
