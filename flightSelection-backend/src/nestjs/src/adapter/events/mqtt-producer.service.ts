/**
 *    Copyright 2019 Sven Loesekann
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import { Injectable, OnModuleDestroy } from "@nestjs/common";
import {connect, IClientOptions, IClientPublishOptions, MqttClient} from "mqtt";
import { Booking } from "src/domain/entity/booking";
import { BookingMapper } from "src/usecase/mapper/booking-mapper.service";
import { deflateSync, inflateSync } from "zlib";

@Injectable()
export class MqttProducerService implements OnModuleDestroy {
    private client: MqttClient;
    private readonly TOPIC_NAME = 'flight-booking';

    constructor(private bookingMapper: BookingMapper) {
        this.client = connect('mqtt://localhost', {username: "artemis1", password: "artemis1"} as IClientOptions);
        this.client.on('connect', (err) => {
            if(!!err) {
                console.log(err);
            }
        });
        /* For local testing.
        this.client.subscribe(this.TOPIC_NAME);
        this.client.on('message', (topic, message) => {            
            console.log(`Topic: ${topic}, Message: ${inflateSync(Buffer.from(message.toString(), 'base64'))}`);
        });
        */
    }
    
    onModuleDestroy() {
        this.client.end();
    }

    sendBooking(booking: Booking, deleted: boolean = false): void {
        const bookingDto = this.bookingMapper.toDto(booking);
        bookingDto.deleted = deleted;
        const zippedBase64 = deflateSync(JSON.stringify(bookingDto)).toString('base64');
        this.client.publish(this.TOPIC_NAME, zippedBase64, {qos: 1} as IClientPublishOptions);
    }
}