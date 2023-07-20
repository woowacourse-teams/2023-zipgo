import { css } from 'styled-components';
import { normalize } from 'styled-normalize';

const globalStyle = css`
  @font-face {
    font-family: Pretendard;
    src: url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css')
      format('css');
  }

  ${normalize}

  * {
    box-sizing: border-box;

    font-size: 62.5%;
  }

  p {
    margin: 0;
  }
`;

export default globalStyle;
