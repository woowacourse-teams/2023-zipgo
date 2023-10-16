const { rules } = require('stylelint-config-clean-order');

const [propertiesOrder, options] = rules['order/properties-order'];

const propertiesOrderWithEmptyLineBefore = propertiesOrder.map(properties => ({
  ...properties,
  emptyLineBefore: 'always',
}));

module.exports = {
  extends: ['stylelint-config-standard', 'stylelint-config-clean-order'],
  customSyntax: 'postcss-styled-syntax',
  rules: {
    'declaration-property-unit-allowed-list': {
      '/^border/': ['px', '%'],
      '/^padding|^gap|^margin|^font/': ['%', 'rem', 'em'],
    },
    'unit-allowed-list': ['%', 'deg', 'px', 'rem', 'ms', 'vw', 'vh', 's'],

    'order/order': [
      'custom-properties',
      'dollar-variables',
      'at-variables',
      'declarations',
      'rules',
      'at-rules',
      'less-mixins',
    ],

    'order/properties-order': [
      propertiesOrderWithEmptyLineBefore,
      {
        ...options,
        severity: 'error',
      },
    ],

    'property-no-vendor-prefix': null,
  },
};
