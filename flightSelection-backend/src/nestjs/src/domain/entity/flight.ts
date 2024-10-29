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
import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export class Flight {
    @PrimaryGeneratedColumn("uuid")
    id: string;
    @Column({name: "flight_number"})
    flightNumber: string;
    @Column()
    airline: string;
    @Column({name: "from_city"})
    fromCity: string;
    @Column({name: "to_city"})
    toCity: string;
    //scale and precision are switched by TypeOrm. -> scale=7,precision=4
    @Column({type: "numeric", scale: 4, precision: 7})
    price: number;
}