# petz-address
Projeto CRUD de endereços (address) 

APIs disponíveis

POST → /petz/v1/address
Esse serviço recebe informações de endereço que será incluído através de um HTTP POST com um JSON que contém o seguinte formato, exemplo:

{
  "streetName" : "rua paulino de brito",
  "number": "612",
  "neighbourhood": "jd brasil",
  "cityId": 1,
  "zipCode": "02200-010"
}
  
GET -> /petz/v1/address/{id}
Esse serviço lista um endereço cadastrado pelo id


PUT -> /petz/v1/address/{id}
Esse serviço recebe informações para alterar um endereço através de um HTTP PUT com um JSON que contém o seguinte formato, exemplo:

{
  "streetName" : "rua paulino de brito",
  "number": "612",
  "neighbourhood": "jd brasil",
  "cityId": 1,
  "zipCode": "02200-010"
}

DELETE -> /petz/v1/address/{id}
Esse serviço exclui um endereço cadastrado pelo id 