import { css } from 'styled-components';
import { normalize } from 'styled-normalize';

const globalStyle = css`
  ${normalize}

  * {
    box-sizing: border-box;

    font-size: 62.5%;
  }
`;

export default globalStyle;
