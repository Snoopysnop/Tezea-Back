#!/bin/bash

getField() {
    local value="$1"
    local key="$2"
    echo "$value" | grep -o "\"$key\":\"[^\"]*" | cut -d":" -f2 | tr -d '"'
}

url="http://148.60.11.163/api"

# create a customer
customer=$(curl -s -X 'POST' \
  $url'customers/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "John",
    "lastName": "Smith",
    "civility": "Mr",
    "email": "john.smith@gmail.com",
    "phoneNumber": "0710101010",
    "address": "3 Rue du Pain",
    "city": "PIPRIAC",
    "postalCode": 35550,
    "status": "Business",
    "company": "LES ANNEES FOLLES"
  }'
)

# create users
bruno=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Bruno",
    "lastName": "Chaveron",
    "role": "Concierge",
    "email": "bruno.chaveron@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

jeanne=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Jeanne",
    "lastName": "Lucas",
    "role": "SiteChief",
    "email": "jeanne.lucas@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

pierre=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Pierre",
    "lastName": "Dumont",
    "role": "WorkSiteChief",
    "email": "pierre.dumont@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

helene=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Helene",
    "lastName": "Blanc",
    "role": "WorkSiteChief",
    "email": "helene.blanc@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

jean=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Jean",
    "lastName": "Signeret",
    "role": "WorkSiteChief",
    "email": "jean.signeret@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

mael=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Mael",
    "lastName": "Dueron",
    "role": "SiteChief",
    "email": "jean.signeret@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

julie=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Julie",
    "lastName": "Pont",
    "role": "Employee",
    "email": "julie.pont@gmail.com",
    "phoneNumber": "0710101010"
  }'
)

sara=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Sara",
    "lastName": "Connor",
    "role": "Employee",
    "email": "sara.connor@gmail.com",
    "phoneNumber": "0710101010"
  }' 
)

george=$(curl -X 'POST' \
  $url'/users/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "George",
    "lastName": "Cort",
    "role": "Employee",
    "email": "george.cort@gmail.com",
    "phoneNumber": "0710101010"
  }' 
)

# create workSiteRequests
curl -X 'POST' \
  $url'/workSiteRequests/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d ' {
    "concierge": "'"$(getField "$bruno" "id")"'",
    "siteChief": "'"$(getField "$jeanne" "id")"'",
    "customer": "'"$(getField "$customer" "id")"'",
    "city": "Rennes",
    "serviceType": "Service",
    "description": "Premiere demande",
    "emergency": "Low",
    "title": "string",
    "category": "Conciergerie",
    "removal": true,
    "delivery": true,
    "removalRecycling": true,
    "chronoQuote": true,
    "estimatedDate": "2024-05-06T10:39:38.148Z",
    "weightEstimate": 0,
    "volumeEstimate": 0,
    "provider": "string",
    "tezeaAffectation": "string"
  }'


curl -X 'POST' \
  $url'/workSiteRequests/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d ' {
    "concierge": "'"$(getField "$bruno" "id")"'",
    "siteChief": "'"$(getField "$jeanne" "id")"'",
    "customer": "'"$(getField "$customer" "id")"'",
    "city": "Rennes",
    "serviceType": "Service",
    "description": "Deuxieme demande",
    "emergency": "Low",
    "title": "string",
    "category": "Conciergerie",
    "removal": true,
    "delivery": true,
    "removalRecycling": true,
    "chronoQuote": true,
    "estimatedDate": "2024-05-06T10:39:38.148Z",
    "weightEstimate": 0,
    "volumeEstimate": 0,
    "provider": "string",
    "tezeaAffectation": "string"
  }'


# create tools
curl -X 'POST' \
  $url'/tools/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Pelle",
    "quantity": 7
  }'

curl -X 'POST' \
  $url'/tools/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Palette",
    "quantity": 12
  }'


# create workSites
curl -X 'POST' \
  $url'/worksites/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "workSiteRequest": "1",
    "workSiteChief": "'"$(getField "$pierre" "id")"'",
    "begin": "2024-05-03T10:12:00.000Z",
    "end": "2024-05-03T10:12:00.000Z",
    "staff": [
      "'"$(getField "$julie" "id")"'"
    ],
    "equipments": {
      "Pelle": 2
    }
    "address": "3 Rue de Rennes",
    "title": "1er chantier"
}'

curl -X 'POST' \
  $url'/worksites/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "workSiteRequest": "1",
    "workSiteChief": "'"$(getField "$pierre" "id")"'",
    "begin": "2024-05-03T10:14:00.000Z",
    "end": "2024-05-03T10:16:00.000Z",
    "staff": [
      "'"$(getField "$julie" "id")"'"
    ],
    "equipments": {
      "Palette": 8
    }
    "address": "15 Rue de Rennes",
    "title": "2e chantier"
}'

curl -X 'POST' \
  $url'/worksites/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "workSiteRequest": "1",
    "workSiteChief": "'"$(getField "$jean" "id")"'",
    "begin": "2024-05-03T10:11:00.000Z",
    "end": "2024-05-03T10:15:00.000Z",
    "staff": [
      "'"$(getField "$george" "id")"'"
    ],
    "equipments": {
      "Pelle" : 3,
      "Palette": 1
    }
    "address": "7 Rue de Rennes",
    "title": "3e chantier"
}'

curl -X 'POST' \
  $url'/worksites/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "workSiteRequest": "2",
    "workSiteChief": "'"$(getField "$helene" "id")"'",
    "begin": "2024-05-03T10:14:00.000Z",
    "end": "2024-05-03T10:18:00.000Z",
    "staff": [
      "'"$(getField "$sara" "id")"'"
    ],
    "equipments": {
      "Pelle" : 1,
      "Palette": 2
    }
    "address": "12 Rue De Paris",
    "title": "Chantier 1"
}'

curl -X 'POST' \
  $url'/worksites/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "workSiteRequest": "2",
    "workSiteChief": "'"$(getField "$jean" "id")"'",
    "begin": "2024-05-03T10:17:00.000Z",
    "end": "2024-05-03T10:20:00.000Z",
    "staff": [
      "'"$(getField "$george" "id")"'"
    ],
    "equipments": {
      "Pelle": 1
    }
    "address": "12 Rue de Paris",
    "title": "Chantier 2"
}'