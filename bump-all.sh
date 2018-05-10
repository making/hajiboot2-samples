#!/bin/bash
set -e

FROM=2.0.1.RELEASE
TO=2.0.2.RELEASE
for f in `find . -name 'pom.xml'`;do
    sed -i.bk "s|<version>${FROM}</version>|<version>${TO}</version>|g" ${f}
    rm -f ${f}.bk
    mvn -f ${f} clean test
done