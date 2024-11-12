workspace "AngularMicroFrontend and Microservice demo" "This project shows howto create 3 Microservices with integrated Microfrontends." {
model {
        bookingUser = person "Booking Portal User" "Use the portal."
        bookingPortalSystem = softwareSystem "Booking Portal System" "Provide the hotels, flights and pay." {
        	flightBooking = container "Flight Booking" "Choose the flights" 
        	hotelBooking = container "Hotel Booking" "Choose the hotels"
        	payment = container "Payment" "Integrate booking flights, hotels and payment"
        	
        }
        
        # System
        bookingUser -> bookingPortalSystem "book hotels, flights and pay"
        # Containers
        payment -> flightBooking "integrate flight booking"
        payment -> hotelBooking "integrate hotel booking"
        
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