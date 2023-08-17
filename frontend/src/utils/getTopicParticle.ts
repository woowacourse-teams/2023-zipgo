export const getTopicParticle = (word: string): string => {
  const topicParticles = ['은', '는'];
  const lastChar = word.at(-1);

  if (!lastChar) return '';

  const hasFinalConsonant = (lastChar.charCodeAt(0) - 44032) % 28 !== 0;

  if (hasFinalConsonant) return topicParticles[0];

  return topicParticles[1];
};
