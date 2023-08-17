import { PropsWithChildren, ReactNode } from 'react';
import { styled } from 'styled-components';

interface InfoBlockProps
  extends PropsWithChildren<{
    headText: string;
    subChild?: ReactNode;
  }> {}

const InfoBlock = (infoBlockProps: InfoBlockProps) => {
  const { headText, children, subChild } = infoBlockProps;

  return (
    <InfoBlockWrapper>
      <Head>
        <InfoHead>{headText}</InfoHead>
        {subChild}
      </Head>

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

const Head = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const InfoHead = styled.h2`
  font-size: 2rem;
  font-weight: 700;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.grey700};
  letter-spacing: -0.05rem;
`;
