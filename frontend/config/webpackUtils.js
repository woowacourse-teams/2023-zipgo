const paths = require('./paths');

const getExternals = moduleList => moduleList.reduce((externals, lib) => ({...externals, lib}), {});

const getLibsToCopyPattern = libList =>
  libList.map(lib => ({
    from: paths.resolveLib(lib),
    to: lib,
  }));

module.exports = {
  getLibsToCopyPattern,
  getExternals,
};
