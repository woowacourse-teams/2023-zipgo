import { PropsWithChildren } from 'react';
import { styled } from 'styled-components';

interface InfoBlockProps
  extends PropsWithChildren<{
    headText: string;
  }> {}

const InfoBlock = (infoBlockProps: InfoBlockProps) => {
  const { headText, children } = infoBlockProps;

  return (
    <InfoBlockWrapper>
      <InfoHead>{headText}</InfoHead>
      {children}
    </InfoBlockWrapper>
  );
};

export default InfoBlock;

const InfoBlockWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const InfoHead = styled.h2`
  font-size: 2rem;
  font-weight: 700;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.grey700};
  letter-spacing: -0.05rem;
`;
