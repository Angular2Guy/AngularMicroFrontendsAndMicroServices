workspace "AngularMicroFrontend and Microservice demo" "This project shows howto create 3 Microservices with integrated Microfrontends." {
model {
        bookingUser = person "Booking Portal User" "Use the portal."
        bookingPortalSystem = softwareSystem "Booking Portal System" "Provide the hotels, flights and pay." {
        	flightBooking = container "Flight Booking" "Choose the flights" 
        	hotelBooking = container "Hotel Booking" "Choose the hotels"
        	payment = container "Payment" "Integrate booking flights, hotels and payment"  {
        		paymentFrontend = component "Payment Frontend" "Payment Frontend" tag "Browser"
        		paymentService = component "Payment Service" "Payment Service"
        		paymentDb = component "Payment Database" "Payment Database" tag "Database"
        		hotelFrontend = component "Hotel Frontend" "Hotel Frontend" tag "Browser"
        		hotelService = component "Hotel Service" "Hotel Service"
        		hotelDb = component "Hotel Database" "Hotel Database" tag "Database"
        		flightFrontend = component "Flight Frontend" "Flight Frontend" tag "Browser"
        		flightService = component "Flight Service" "Flight Service"
        		flightDb = component "Flight Database" "Flight Database" tag "Database"        	
        	}        	
        }
        
        # System
        bookingUser -> bookingPortalSystem "book hotels, flights and pay"
        # Containers
        payment -> flightBooking "integrate flight booking"
        payment -> hotelBooking "integrate hotel booking"
        # Component
        paymentFrontend -> paymentService
        paymentService -> paymentDb
        hotelFrontend -> hotelService
        hotelService -> hotelDb
        flightFrontend -> flightService
        flightService -> flightDb
        paymentFrontend -> hotelFrontend
        paymentFrontend -> flightFrontend
        hotelService -> paymentService "Queue"
        flightService -> paymentService "Queue"
        }
views {
        systemContext bookingPortalSystem "SystemContext" {
            include *
            autoLayout
        }                 
        
        container bookingPortalSystem "Containers" {
            include *
            autoLayout
        }
        
        component payment "Components" {
            include *
            autoLayout
        }        
        
        styles {
        	element "Person" {            
            	shape Person
        	}
        	element "Database" {
                shape Cylinder                
            }
            element "Browser" {
                shape WebBrowser
            }
            element "Consumer" {
            	shape Pipe
            }
            element "Scheduler" {
            	shape Circle
            }
        }
    }
}