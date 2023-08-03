import { styled } from 'styled-components';

import ResearchIcon from '@/assets/svg/research_icon.svg';
import VetIcon from '@/assets/svg/vet_icon.svg';
import { Brand } from '@/types/food/client';

interface BrandBlockProps extends Brand {}

const BrandBlock = (brandBlockProps: BrandBlockProps) => {
  const { name, imageUrl, state, foundedYear, hasResidentVet, hasResearchCenter } = brandBlockProps;

  return (
    <BrandBlockWrapper>
      <BrandProfile>
        <BrandImage src={imageUrl} alt={name} />
        <BrandInfo>
          <BrandName>{name}</BrandName>
          <BrandFoundedYear>{`창립연도 ${foundedYear}년`}</BrandFoundedYear>
        </BrandInfo>
      </BrandProfile>
      <BrandPersonalityList>
        <BrandPersonality>
          <BrandPersonalityImg
            src={
              state === '미국'
                ? 'data:image/png;base64,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'
                : 'https://media.istockphoto.com/id/934017954/ko/%EB%B2%A1%ED%84%B0/%EC%BA%90%EB%82%98%EB%8B%A4-flag.jpg?s=612x612&w=0&k=20&c=Bzikss7IgenHP791GUGSdNCaansgET67HRJxc3UgXrk='
            }
            alt={`${state} 브랜드`}
          />
          <BrandPersonalityText>{state}</BrandPersonalityText>
        </BrandPersonality>
        {hasResidentVet && (
          <BrandPersonality>
            <BrandPersonalityImg src={VetIcon} alt="수의사 상주 여부" />
            <BrandPersonalityText>수의사 상주</BrandPersonalityText>
          </BrandPersonality>
        )}
        {hasResearchCenter && (
          <BrandPersonality>
            <BrandPersonalityImg src={ResearchIcon} alt="연구센터 여부" />
            <BrandPersonalityText>연구센터</BrandPersonalityText>
          </BrandPersonality>
        )}
      </BrandPersonalityList>
    </BrandBlockWrapper>
  );
};

export default BrandBlock;

const BrandBlockWrapper = styled.div`
  padding: 2rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
`;

const BrandProfile = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;

  padding-bottom: 2rem;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
`;

const BrandImage = styled.img`
  width: 9rem;
  height: 9rem;

  object-fit: cover;
  border-radius: 20px;
`;

const BrandInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
`;

const BrandName = styled.h3`
  font-size: 2rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey500};
  letter-spacing: -0.05rem;
`;

const BrandFoundedYear = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;

const BrandPersonalityList = styled.ul`
  display: flex;
  gap: 4.8rem;
  justify-content: center;

  padding: 2rem 0 0;

  list-style: none;
`;

const BrandPersonality = styled.li`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;
`;

const BrandPersonalityImg = styled.img`
  width: 5.5rem;
  height: 5.5rem;

  object-fit: cover;
  border-radius: 10px;
`;

const BrandPersonalityText = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;
