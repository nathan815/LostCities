module.exports = {
    preset: '@vue/cli-plugin-unit-jest/presets/typescript-and-babel',
    testMatch: ['<rootDir>/(src/**/*.test.(ts|tsx|js)|**/__tests__/*.(ts|tsx|js))'],
    collectCoverage: true,
    collectCoverageFrom: [
        'src/**/*.{js,ts,vue}',
        '!src/main.ts',
        '!src/registerServiceWorker.ts',
    ],
    coverageReporters: ['html', 'text', 'text-summary'],
};
