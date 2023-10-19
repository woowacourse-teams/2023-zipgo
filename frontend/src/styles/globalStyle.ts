import { css } from 'styled-components';
import { normalize } from 'styled-normalize';

const globalStyle = css`
  @font-face {
    font-family: Pretendard;
    src: url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css')
      format('css');
  }

  ${normalize}

  :root {
    --vh: 100%;
  }

  * {
    scrollbar-width: none;

    box-sizing: border-box;

    font-size: 62.5%;

    &::-webkit-scrollbar {
      width: 0;
      height: 0;
    }
    /* stylelint-disable-next-line media-feature-range-notation */
    @media (max-width: 392px) {
      font-size: 50%;
    }
  }

  body {
    font-family: Pretandard, sans-serif;
  }

  a {
    text-decoration: none;
  }

  button,
  ul,
  li,
  h1,
  h2,
  h3,
  p {
    margin: 0;
    padding: 0;
  }

  button {
    cursor: pointer;
  }

  input,
  textarea {
    /* stylelint-disable-next-line declaration-property-unit-allowed-list */
    font-size: 16px;

    -webkit-appearance: none;
    appearance: none;
    -webkit-border-radius: 0;
    border-radius: 0;
  }

  select {
    /* stylelint-disable-next-line declaration-property-unit-allowed-list */
    font-size: 16px;

    -webkit-appearance: none;
    appearance: none;
    background-color: transparent;
    -webkit-border-radius: 0;
    border-radius: 0;
    outline: none;
  }
`;

export default globalStyle;
