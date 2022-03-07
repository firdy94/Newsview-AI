export interface Article {
    id: string;
    website: string
    author: string
    title: string
    description: string
    urlPath: string
    imagePath: string
    publishedDate: string
}

export interface ArticleAnalysis {
    id: string;
    website: string
    author: string
    title: string
    description: string
    urlPath: string
    imagePath: string
    publishedDate: string
    sentimentAnalysis: {
        entities: [
            {
                type: string,
                text: string,
                confidence: number,
                emotion: {
                    anger: number,
                    disgust: number,
                    fear: number,
                    joy: number,
                    sadness: number
                },
                results: {
                    score: number
                }
            }
        ],
        categories: [
            {
                label: string,
                score: number,
            }
        ],
        keywords: [
            {
                relevance: number,
                text: string,
                emotion: {
                    anger: number,
                    disgust: number,
                    fear: number,
                    joy: number,
                    sadness: number
                },
                score: {
                    score: number
                }
            }
        ],
        sentiment: {
            label: string,
            score: number
        },
        targets: [
            {
                text: "apples",
                emotion: {
                    sadness: number,
                    joy: number,
                    fear: number,
                    disgust: number,
                    anger: number
                }
            }
        ]
    },
    tonesAnalysis: {
        documentTone: {
            toneScores: [
                {
                    score: number,
                    toneId: string,
                    toneName: string
                }
            ],
            // toneCategories: null
        },
        "sentenceTones": [
            {
                text: string,
                toneScores: [
                    {
                        score: number,
                        toneId: string,
                        toneName: string
                    }
                ],

            }
        ]
    }
}

export interface message {
    name: string
    email: string
    message: string
}