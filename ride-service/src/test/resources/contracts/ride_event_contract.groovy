import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Send driver event to Kafka topic"

    input {
        triggeredBy('listenDriverEvents("driver123")')
    }

    outputMessage {
        sentTo 'driver-events'
        body("driver123")
        headers {
            header('contentType', applicationJson())
        }
    }
}
