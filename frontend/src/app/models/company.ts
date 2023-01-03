import { City } from "./city";
import { Country } from "./country";

export interface Company {
  id: number;
  name: string;
  photo: string;
  description: string;
  cityId: number;
  city?: City;
  country?: Country;
}
