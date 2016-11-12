#!/bin/bash
# Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License"). You may
# not use this file except in compliance with the License. A copy of the
# License is located at
#
#	http://aws.amazon.com/apache2.0/
#
# or in the "license" file accompanying this file. This file is distributed
# on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
# express or implied. See the License for the specific language governing
# permissions and limitations under the License.
#
# This script generates a file in go with the license contents as a constant

set -e
outputfile=${1?Must provide an output file}
inputfile="$(<../LICENSE)"

appendRepoLicense() {
  repo=$1
  echo "Adding license for ${repo}"
  inputfile+=$'\n'"***"$'\n'"$repo"$'\n\n'
  # Copy LICENSE* files
  for licensefile in $repo/LICENSE*; do
    if [ -f $licensefile ]; then
      inputfile+="$(<$licensefile)"$'\n'
    fi;
  done;
  # Copy COPYING* file
  if [ -f $repo/COPYING* ]; then
    inputfile+="$(<$repo/COPYING*)"$'\n'
  fi;
}

for registry in github.com golang.org; do
  for user in ./../vendor/$registry/*; do
    for repo in $user/*; do
      appendRepoLicense $repo
    done;
  done;
done;

for repo in ./../vendor/gopkg.in/* ./../vendor/google.golang.org/*; do
  appendRepoLicense $repo
done;

cat << EOF > "${outputfile}"
// Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License"). You may
// not use this file except in compliance with the License. A copy of the
// License is located at
//
//     http://aws.amazon.com/apache2.0/
//
// or in the "license" file accompanying this file. This file is distributed
// on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language governing
// permissions and limitations under the License.

package licenses

const License = \`$inputfile\`
EOF