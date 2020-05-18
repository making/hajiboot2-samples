#!/bin/bash
set -xe

FROM=2.2.7.RELEASE
TO=2.3.0.RELEASE
for f in `find . -name 'pom.xml'`;do
    sed -i.bk "s|<version>${FROM}</version>|<version>${TO}</version>|g" ${f}
    rm -f ${f}.bk
    mvn -f ${f} clean test
done
