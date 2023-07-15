module.exports = {
  extends: ['stylelint-config-standard', 'stylelint-config-clean-order'],
  plugins: ['stylelint-order'],
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
  },
};
