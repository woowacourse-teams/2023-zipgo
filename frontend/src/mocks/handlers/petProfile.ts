import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { PetProfile } from '@/types/petProfile/client';

import petProfileFixture from '../fixtures/petProfile';

const petProfileHandlers = [
  rest.get(`${BASE_URL}/pets/breeds`, (req, res, ctx) => {
    const breeds = petProfileFixture.getBreeds();

    return res(ctx.status(200), ctx.json(breeds));
  }),

  rest.get(`${BASE_URL}/pets/:petId`, (req, res, ctx) => {
    const { pets } = petProfileFixture.getPets();
    const pet = pets.find(pet => pet.id === Number(req.params.petId)) as PetProfile;

    return res(ctx.status(200), ctx.json(pet));
  }),

  rest.get(`${BASE_URL}/pets`, (req, res, ctx) => {
    const pets = petProfileFixture.getPets();

    return res(ctx.status(200), ctx.json(pets));
  }),

  rest.post(`${BASE_URL}/pets`, (req, res, ctx) => res(ctx.status(201))),

  rest.put(`${BASE_URL}/pets/:petId`, (req, res, ctx) => res(ctx.status(204))),

  rest.delete(`${BASE_URL}/pets/:petId`, (req, res, ctx) => res(ctx.status(204))),
];

export default petProfileHandlers;
