const fs = require('fs');
const path = require('path');

const appDirectory = fs.realpathSync(process.cwd());

const buildPaths = ['dist', 'build'];

const resolveApp = relativePath => path.resolve(appDirectory, relativePath);
const resolveFile = file => require.resolve(file);

const resolveBuildPaths = modulePath =>
  buildPaths.find(buildPath => fs.existsSync(resolveApp(`${modulePath}/${buildPath}`)));

const resolveLib = lib => {
  const libPath = `${path.dirname(resolveFile(`${lib}/package.json`))}`;
  const buildPath = resolveBuildPaths(libPath);

  return `${path.dirname(resolveFile(`${lib}/package.json`))}/${buildPath}`;
};

const moduleFileExtensions = ['js', 'ts', 'tsx', 'json', 'jsx'];

const resolveModule = (resolveFn, filePath) => {
  const extension = moduleFileExtensions.find(extension =>
    fs.existsSync(resolveFn(`${filePath}.${extension}`)),
  );

  if (extension) {
    return resolveFn(`${filePath}.${extension}`);
  }

  return resolveFn(`${filePath}.js`);
};

module.exports = {
  appHtml: resolveApp('public/index.html'),
  appFavicon: resolveApp('public/favicon.ico'),
  appIndex: resolveModule(resolveApp, 'src/index'),
  appOutput: resolveApp('dist'),
  appTsConfig: resolveApp('tsconfig.json'),
  dotenv: resolveApp('.env'),
  moduleFileExtensions,
  'alias@': resolveApp('src/'),
  resolveLib,
};
