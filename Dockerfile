FROM openshift/wildfly-101-centos7
ADD ROOT.war /wildfly/standalone/deployments/ROOT.war
#RUN sh /wildfly/bin/standalone.sh
