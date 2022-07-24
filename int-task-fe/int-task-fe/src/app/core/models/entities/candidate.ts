import { Skill } from "./skill";

export interface Candidate{
    id: number;
    fullName: string;
    birthdate: string;
    contactNumber: string;
    email: string;
    skills: Skill[];
}