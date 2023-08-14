var path = require('path');

module.exports = {
    resolve: {
        extensions: ['.js', '.jsx']
    },
    context: path.resolve(__dirname, "..", ".."),
    entry: './src/index.js',
    devtool: 'eval-cheap-module-source-map',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, 'target', 'classes', 'static', 'js'),
        filename: 'bundle.js'
    },

    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.scss$/,
                include: [
                    path.resolve(__dirname, 'frontend', 'src', 'assets', "scss")
                ],
                use: [
                    {loader:'style-loader'},
                    {loader:'css-loader'},
                    {loader:'sass-loader'}
                ]
            },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|svg|jpg|gif|eot|otf|ttf|woff|woff2)$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {}
                    }
                ]
            }
        ]
    }
};