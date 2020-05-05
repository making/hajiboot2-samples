#!/bin/bash
set -x

FROM=2.2.0.M4
TO=2.2.6.RELEASE
for f in `find . -name 'pom.xml'`;do
    sed -i.bk "s|<version>${FROM}</version>|<version>${TO}</version>|g" ${f}
    rm -f ${f}.bk
    # mvn -f ${f} clean test
done