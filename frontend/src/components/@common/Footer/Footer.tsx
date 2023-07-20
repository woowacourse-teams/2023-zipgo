import { styled } from 'styled-components';

const Footer = () => (
  <FooterContainer>
    <FooterTitle>©️ 2023 집사의고민</FooterTitle>
    <FooterDescription>
      본 서비스는 집사들의 반려동물 사료를 맞춤형으로 더욱 간편하게 정보를 확인하기 위해
      기획되었습니다.
    </FooterDescription>
  </FooterContainer>
);

export default Footer;

const FooterContainer = styled.footer`
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 100vw;
  height: 127px;
  padding: 3.2rem;

  background-color: #333d4b;
`;

const FooterTitle = styled.p`
  margin-bottom: 1.2rem;

  font-size: 1.6rem;
  font-weight: bold;
  color: #f2f4f6;
`;

const FooterDescription = styled.p`
  font-size: 1.2rem;
  color: #f2f4f6;
`;
