# Shape Backend Developer Test
## FPSO Equipment Management

### Technologies used

- Java 11
- Spring Boot/Spring JPA - Hibernate
- PostgreSQL database
- Gson
- Docker
- Maven

### Necessary to run the project
To run this project is necessary to have docker and docker-compose.

Links to install:
* Docker: https://docs.docker.com/install/linux/docker-ce/ubuntu/
* Docker-compose: https://docs.docker.com/compose/install/

### Running the project
The docker will create the DB and up the project.

* Command to run: docker-compose up

As all is executed the DB will be created and the project will be running.

### Endpoint usage

The endpoints are accessible via http://localhost:5000/{endpoint}

### List of endpoints

- #### http://localhost:5000/vessel/

Returns the list of vessels.

- #### http://localhost:5000/vessel/insert_vessel

Register a new vessel. <br>
OBS: the vessel code is unique.
<br>
<br>
Payload:
```json
{
  "code" : "{vessel_code}"
}
```

- #### http://localhost:5000/vessel/avg_cost_by_vessel/

Return the average cost in operation for each vessel.

- #### http://localhost:5000/equipment/

Returns the list of equipments.

- #### http://localhost:5000/equipment/insert_equipment

Register a new equipment
OBS: the equipment code is unique.
<br>
<br>
Payload:
```json
{
  "vessel_code" : "{vesselCode}",
  "equipment" : {
    "name": "{equipmentName}",
    "code": "{equipmentCode}",
    "location": "{equipmentLocation}"
  }
}
```

- #### http://localhost:5000/equipment/update_equipment_status

Set equipments status to inactive.
<br>
<br>

Request Header:
```

equipmentCodes: {equipmentCode}, {equipmentCode}

```

- #### http://localhost:5000/equipment/active_equipments/{vesselCode}

Returns all active equipments of a vessel.

- #### http://localhost:5000/equipment/register_operation

Register a new operation with a cost to equipment.
<br>
<br>

Payload:
```json
{
	"code": "{equipmentCode}",
	"type": "replacement", 
	"cost": "10000"
}
```

- #### http://localhost:5000/cost_by_code/{equipmentCode}

Returns the total operation cost of equipment by code

- #### http://localhost:5000/cost_by_name/{equipmentName}

Returns the total operation cost of equipment by name