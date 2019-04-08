# ak
### API:

### Device
Post /shop-api/device  
{    
        "price" : "2300.50",  
	"model" : "MiBand 3",  
	"color" : "BLACK",  
	"date" : "01.01.2018",  
	"manufacturer" : "Xiaomi"  
}   

Get /shop-api/device/{id}

Query Params for device: priceTo, priceFrom, dateTo, dateFrom, color, model, manufacturer

Example:
Get /shop-api/device/filter?priceTo=2350&priceFrom=2300&color=BLACK

### Client
Post /shop-api/client  
{  
	"firstName" : "Vasilii",  
	"lastName" : "Petrov",  
	"patronymic" : "Petrovich",  
	"birthDate" : "12.12.1994"  
}  

Get /shop-api/client/{id}

Query Params for client: firstName, lastName, patronymic, birthDateFrom, birthDateTo

Example:
Get /shop-api/client/filter?lastName=Petrov

