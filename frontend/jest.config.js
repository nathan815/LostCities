module.exports = {
    preset: '@vue/cli-plugin-unit-jest',
    testMatch: [
        '<rootDir>/(src/**/*.test.(ts|tsx|js)|**/__tests__/*.(ts|tsx|js))',
    ],
    collectCoverage: true,
    collectCoverageFrom: [
        'src/**/*.{js,vue}',
        '!src/main.js', // No need to cover main file
    ],
};
