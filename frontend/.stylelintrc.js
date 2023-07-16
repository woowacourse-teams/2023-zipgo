const { rules } = require('stylelint-config-clean-order');

const [propertiesOrder, options] = rules['order/properties-order'];

module.exports = {
  extends: ['stylelint-config-standard', 'stylelint-config-clean-order'],
  customSyntax: 'postcss-styled-syntax',
  rules: {
    'declaration-property-unit-allowed-list': {
      '/^border/': ['px'],
      '/^padding|^gap|^margin|^font/': ['rem', 'em'],
    },
    'unit-allowed-list': ['%', 'deg', 'px', 'rem', 'ms'],

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
      propertiesOrder,
      {
        ...options,
        severity: 'error',
      },
    ],
  },
};
