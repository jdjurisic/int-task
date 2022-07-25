import { Skill } from "./skill";

export interface Candidate{
    id: number;
    fullName: string;
    birthdate: Date;
    contactNumber: string;
    email: string;
    skills: Skill[];
}