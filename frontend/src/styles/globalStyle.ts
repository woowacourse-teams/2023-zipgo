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
    box-sizing: border-box;

    font-size: 62.5%;
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
`;

export default globalStyle;
