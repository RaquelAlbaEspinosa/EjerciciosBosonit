const path = require('path');

module.exports = {
    entry: path.join(__dirname, '/modules.ts'),
    output: {
        filename: 'modules.js',
        path: __dirname,
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loader: 'ts-loader',
                exclude: /node_modules/,
            },
        ]
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"]
    },
    devServer: {
        static: {
            directory: __dirname,
        },
        port: 8080,
    },
};
