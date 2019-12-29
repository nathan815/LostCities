// https://docs.cypress.io/api/introduction/api.html

describe('Example Test', () => {
    it('Visits the app root url', () => {
        cy.visit('/');
        cy.contains('h1', 'Lost Cities');
    });
});
