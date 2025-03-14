import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Reserve a ride"

    request {
        method POST()
        urlPath("/api/rides/reserve")
        body([
                passengerId: "passenger123",
                driverId   : "driver123",
                startLocation: "Start Point",
                endLocation: "End Point",
                status: "RESERVED"
        ])
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 200
        body([
                passengerId: "passenger123",
                driverId   : "driver123",
                status: "RESERVED"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}
