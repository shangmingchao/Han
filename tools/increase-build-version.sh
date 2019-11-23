#!/bin/bash

while read -r line; do
  if [[ "$line" =~ ^versionBuild ]]; then
    build=$(echo "$line" | awk '/versionBuild/' | awk '{print $3}')
    build=$((build + 1))
    src="$line"
    result="versionBuild = $build"
    break
  fi
done <version.properties
if [[ -n $result ]]; then
  echo "$result"
  sed -i '' 's/'"$src"'/'"$result"'/g' version.properties
fi
