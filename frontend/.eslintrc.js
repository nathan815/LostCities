module.exports = {
    root: true,

    env: {
        node: true,
        jest: true,
    },

    extends: ['plugin:vue/essential', '@vue/prettier', '@vue/typescript'],

    rules: {
        'no-unused-vars': 'warn',
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    },

    parserOptions: {
        parser: '@typescript-eslint/parser',
    },

    overrides: [
        {
            files: [
                '**/__tests__/*.{j,t}s?(x)',
                '**/tests/unit/**/*.spec.{j,t}s?(x)',
            ],
            env: {
                jest: true,
            },
        },
    ],
};
